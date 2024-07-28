package com.zerobase.springbootjpapractice.user.service;

import com.zerobase.springbootjpapractice.board.model.ServiceResult;
import com.zerobase.springbootjpapractice.common.MailComponent;
import com.zerobase.springbootjpapractice.common.exception.BizException;
import com.zerobase.springbootjpapractice.logs.service.LogService;
import com.zerobase.springbootjpapractice.mail.entity.MailTemplate;
import com.zerobase.springbootjpapractice.mail.repository.MailTemplateRepository;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.entity.UserInterest;
import com.zerobase.springbootjpapractice.user.model.*;
import com.zerobase.springbootjpapractice.user.repository.UserCustomRepository;
import com.zerobase.springbootjpapractice.user.repository.UserInterestRepository;
import com.zerobase.springbootjpapractice.user.repository.UserRepository;
import com.zerobase.springbootjpapractice.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCustomRepository userCustomRepository;
    private final UserInterestRepository userInterestRepository;
    private final LogService logService;
    private final MailComponent mailComponent;
    private final MailTemplateRepository mailTemplateRepository;

    @Override
    public UserSummary getUserStatusCount() {
        long usingUserCount = userRepository.countByStatus(UserStatus.USING);
        long stopUserCount = userRepository.countByStatus(UserStatus.STOP);
        long totalUserCount = userRepository.count();

        return UserSummary.builder()
                .usingUserCount(usingUserCount)
                .stopUserCount(stopUserCount)
                .totalUserCount(totalUserCount)
                .build();
    }

    @Override
    public List<User> getTodayUsers() {
        LocalDateTime t = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(t.getYear(), t.getMonth(), t.getDayOfMonth(), 0, 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);
        return userRepository.findByToday(startDate, endDate);
    }

    @Override
    public List<UserNoticeCount> getUserNoticeCount() {
        return List.of();
    }

    @Override
    public List<UserLogCount> getUserLogCount() {
        return userCustomRepository.findUserLogCount();
    }

    @Override
    public List<UserLogCount> getUserLikeBest() {
        return userCustomRepository.findUserLikeBest();
    }

    @Override
    public ServiceResult addInterestUser(String email, Long id) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        Optional<User> optionalInterestUser = userRepository.findById(id);
        if(optionalInterestUser.isEmpty()){
            return ServiceResult.fail("관심사용자의 사용자가 존재하지 않습니다.");
        }
        User interestUser = optionalInterestUser.get();

        if(user.getId().equals(interestUser.getId())){
            return ServiceResult.fail("자기자신은 추가할 수 없습니다.");
        }
        if(userInterestRepository.countByUserAndAndInterestUser(user, interestUser)>0){
            return ServiceResult.fail("이미 관심사용자 목록에 추가하였습니다.");
        }

        UserInterest userInterest = UserInterest.builder()
                .user(user)
                .interestUser(interestUser)
                .regDate(LocalDateTime.now()).build();

        userInterestRepository.save(userInterest);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeInterestUser(String email, Long id) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("사용자가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        Optional<UserInterest> optionalUserInterest = userInterestRepository.findById(id);
        if(optionalUserInterest.isEmpty()){
            return ServiceResult.fail("삭제할 정보가 없습니다.");
        }

        UserInterest userInterest = optionalUserInterest.get();

        if(userInterest.getUser().getId().equals(user.getId())){
            return ServiceResult.fail("본인의 관심사 정보만 삭제할 수 있습니다.");
        }
        userInterestRepository.delete(userInterest);
        return null;
    }

    @Override
    public User login(UserLogin userLogin) {
        Optional<User> optionalUser = userRepository.findByEmail(userLogin.getEmail());
        if(optionalUser.isEmpty()){
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        if(PasswordUtils.equalPassword(userLogin.getPassword(), user.getPassword())){
            throw new BizException("일치하는 정보가 없습니다.");
        }

        logService.add("로그인 시도");

        return user;
    }

    @Override
    public ServiceResult addUser(UserInput input) {
        Optional<User> optionalUser = userRepository.findByEmail(input.getEmail());
        if(optionalUser.isEmpty()){
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        String encryptPassword = PasswordUtils.encryptPassword(input.getPassword());
        User user = User.builder()
                .email(input.getEmail())
                .userName(input.getUserName())
                .regDate(LocalDateTime.now())
                .password(encryptPassword)
                .phone(input.getPhone())
                .status(UserStatus.USING)
                .build();
        userRepository.save(user);

        String fromEmail = "@gmail.com";
        String fromName = "관리자";
        String toEmail = input.getEmail();
        String toName = input.getUserName();

        String title = "회원가입 완료";
        String content = "회원가입 축하드립니다.";

        mailComponent.send(fromEmail,fromName,toEmail,toName,title,content);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult resetPassword(UserPasswordResetInput input) {
        Optional<User> optionalUser = userRepository.findByEmailAndUserName(input.getEmail(), input.getUserName());
        if(optionalUser.isEmpty()){
            throw new BizException("회원정보가 존재하지 않습니다.");
        }
        User user= optionalUser.get();

        String passwordResetKey = UUID.randomUUID().toString();

        user.setPasswordResetYn(true);
        user.setPasswordResetKey(passwordResetKey);
        userRepository.save(user);

        String serverUrl ="http://localhost:8080";
        Optional<MailTemplate> optionalUserResetPassword = mailTemplateRepository.findByTemplateId("USER_RESET_PASSWORD");
        if(optionalUserResetPassword.isPresent()){
            MailTemplate mailTemplate = optionalUserResetPassword.get();
            String fromEmail = mailTemplate.getSendEmail();
            String fromUserName = mailTemplate.getSendUserName();
            String title = mailTemplate.getTitle().replaceAll("\\{USER_NAME\\}}", user.getUserName());
            String content = mailTemplate.getContent().replaceAll("\\{USER_NAME\\}", user.getUserName())
                    .replaceAll("\\{SERVER_URL\\}", serverUrl)
                    .replaceAll("\\{RESET_PASSWORD_KEY\\}",passwordResetKey);
            mailComponent.send(fromEmail, fromUserName, user.getEmail(), user.getUserName(), title, content);
        }

        return ServiceResult.success();
    }

    @Override
    public void sendServiceNotice() {
        List<User> users = userRepository.findAll()
                .stream().filter(user -> user.getRegDate().isBefore(LocalDateTime.now().minusYears(1)))
                .collect(Collectors.toList());

        Optional<MailTemplate> optionalTemplate = mailTemplateRepository.findByTemplateId("USER_SERVICE_NOTICE");
        if(optionalTemplate.isPresent()){
            MailTemplate mailTemplate = optionalTemplate.get();
            String fromEmail = mailTemplate.getSendEmail();
            String fromUserName = mailTemplate.getSendUserName();
            users.stream().map(user -> {
                String title = mailTemplate.getTitle().replaceAll("\\{USER_NAME\\}}", user.getUserName());
                String content = mailTemplate.getContent();
                mailComponent.send(fromEmail, fromUserName, user.getEmail(), user.getUserName(), title, content);
                return true;
            });
        }

    }
}
