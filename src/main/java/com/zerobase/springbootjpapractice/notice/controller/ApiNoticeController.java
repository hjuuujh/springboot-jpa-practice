package com.zerobase.springbootjpapractice.notice.controller;

import com.zerobase.springbootjpapractice.notice.entity.Notice;
import com.zerobase.springbootjpapractice.notice.exception.AlreadyDeletedException;
import com.zerobase.springbootjpapractice.notice.exception.NoticeNotFoundException;
import com.zerobase.springbootjpapractice.notice.exception.DuplicateNoticeException;
import com.zerobase.springbootjpapractice.notice.model.NoticeDeleteInput;
import com.zerobase.springbootjpapractice.notice.model.NoticeInput;
import com.zerobase.springbootjpapractice.notice.model.NoticeModel;
import com.zerobase.springbootjpapractice.notice.model.ResponseError;
import com.zerobase.springbootjpapractice.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ApiNoticeController {

    private final NoticeRepository noticeRepository;
    private final LocalDateTime now = LocalDateTime.now();
    private final List<NoticeModel> noticeModels = new ArrayList<>();

    @GetMapping("/api/notice")
    public String noticeString() {
        return "공지사항입니다.";
    }

    @GetMapping("/api/notice2")
    public NoticeModel notice() {
        // 게시글Id=1, 제목 = 공지사항입니다, 내용 = 공지사항 내용입니다, 등록일 = 224-07-22
        NoticeModel noticeModel = new NoticeModel();
        noticeModel.setId(1);
        noticeModel.setTitle("공지사항입니다.");
        noticeModel.setContents("공지사항 내용입니다.");
        noticeModel.setRegDate(now);

        return noticeModel;
    }

    @GetMapping("/api/notice3")
    public List<NoticeModel> noticeList() {
        // 게시글Id=1, 제목 = 공지사항입니다, 내용 = 공지사항 내용입니다, 등록일 = 224-07-22

        noticeModels.add(NoticeModel.builder()
                .id(1)
                .title("공지사항입니다.")
                .contents("공지사항 내용입니다.")
                .regDate(now)
                .build());

        noticeModels.add(NoticeModel.builder()
                .id(2)
                .title("두번째 공지사항입니다.")
                .contents("두번째 공지사항 내용입니다.")
                .regDate(now.minusDays(1))
                .build());

        return noticeModels;
    }

    @GetMapping("/api/notice4")
    public List<NoticeModel> noticeEmptyList() {
        // 빈 배열 리턴

        return noticeModels;
    }

    @GetMapping("/api/notice/count")
    public int noticeCount() {
        // 리턴값은 게시판의 게시글 개수(정수)를 리턴
        // 컨트롤러에서 정수형을 리턴해도 클라이언트 쪽에 내려가는 부분은 문자열

        return 10;
    }

    @PostMapping("/api/notice")
    public NoticeModel addNotice(@RequestParam String title, @RequestParam String content) {

        NoticeModel notice = NoticeModel.builder()
                .id(1)
                .title(title)
                .contents(content)
                .regDate(now)
                .build();

        return notice;
    }

    @PostMapping("/api/notice2")
    public NoticeModel addNoticeFromModel(NoticeModel noticeModel) {

        noticeModel.setId(2);
        noticeModel.setRegDate(now);

        return noticeModel;
    }

    @PostMapping("/api/notice3")
    public NoticeModel addNoticeFromJson(@RequestBody NoticeModel noticeModel) {

        noticeModel.setId(3);
        noticeModel.setRegDate(now);

        return noticeModel;
    }

    @PostMapping("/api/notice4")
    public Notice addNoticeSaveToDb(@RequestBody NoticeInput input) {

        Notice notice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .regDate(now)
                .build();


        return noticeRepository.save(notice);
    }

    @PostMapping("/api/notice5")
    public Notice addNoticeSaveToDbWithHitAndLike(@RequestBody NoticeInput input) {

        Notice notice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .regDate(now)
                .hits(0)
                .likes(0)
                .build();

        return noticeRepository.save(notice);
    }

    @GetMapping("/api/notice/{id}")
    public Notice getNotice(@PathVariable Long id) {
        Optional<Notice> notice = noticeRepository.findById(id);

        if(notice.isPresent()) {
            return notice.get();
        }

        return null;
    }

    @PutMapping("/api/notice/{id}")
    public void updateNotice(@PathVariable Long id, @RequestBody NoticeInput input) {
        Optional<Notice> notice = noticeRepository.findById(id);

        if(notice.isPresent()) {
            notice.get().setTitle(input.getTitle());
            notice.get().setContents(input.getContents());
            notice.get().setUpdateDate(now);
            noticeRepository.save(notice.get());
        }

    }

    @ExceptionHandler(NoticeNotFoundException.class)
    public ResponseEntity<String> handlerNoticeNotFoundException(NoticeNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/api/notice2/{id}")
    public void updateNoticeHandleError(@PathVariable Long id, @RequestBody NoticeInput input) {
//        Optional<Notice> notice = noticeRepository.findById(id);
//
//        if(!notice.isPresent()) {
//            throw new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다.");
//        }
//        notice.get().setTitle(input.getTitle());
//        notice.get().setContents(input.getContents());
//        notice.get().setUpdateDate(now);
//        noticeRepository.save(notice.get());

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));
        notice.setTitle(input.getTitle());
        notice.setContents(input.getContents());
        noticeRepository.save(notice);
    }

    @PutMapping("/api/notice3/{id}")
    public void updateNoticeWithUpdateDate(@PathVariable Long id, @RequestBody NoticeInput input) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));
        notice.setTitle(input.getTitle());
        notice.setContents(input.getContents());
        notice.setUpdateDate(now);
        noticeRepository.save(notice);
    }

    @PatchMapping("/api/notice/{id}/hits")
    public void noticeHits(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));
        notice.setHits(notice.getHits() + 1);
        notice.setUpdateDate(now);
        noticeRepository.save(notice);
    }

    @DeleteMapping("/api/notice/{id}")
    public void deleteNotice(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        noticeRepository.delete(notice);
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<String> handlerAlreadyDeletedException(AlreadyDeletedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    @DeleteMapping("/api/notice2/{id}")
    public void deleteNoticeWithFlag(@PathVariable Long id) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다."));

        if(notice.isDeleted()){
            throw new AlreadyDeletedException("이미 삭제된 글입니다.");
        }
        notice.setDeleted(true);
        notice.setDeletedDate(now);
        noticeRepository.save(notice);
    }

    @DeleteMapping("/api/notices}")
    public void deleteNoticeList(@RequestBody NoticeDeleteInput input) {
        List<Notice> noticeList = noticeRepository.findByIdIn(input.getIdList());
        if(noticeList.isEmpty()){
            throw new NoticeNotFoundException("공지사항의 글이 존재하지 않습니다.");
        }
        noticeList.stream().forEach(notice -> {
            notice.setDeleted(true);
            notice.setDeletedDate(now);
        });

        noticeRepository.saveAll(noticeList);
    }

    @DeleteMapping("/api/notice/all")
    public void deleteAllNotice() {
        noticeRepository.deleteAll();
    }

    @PostMapping("/api/notice6")
    public void addNoticeFromDto(@RequestBody NoticeInput input) {
        Notice notice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .hits(0)
                .likes(0)
                .regDate(now)
                .build();

        noticeRepository.save(notice);
    }

    @PostMapping("/api/notice7")
    public ResponseEntity<?> addNoticeWithBlankValidation(@RequestBody @Valid NoticeInput input,
                                        Errors errors) {
        if(errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        Notice notice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .hits(0)
                .likes(0)
                .regDate(now)
                .build();

        noticeRepository.save(notice);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/notice8")
    public ResponseEntity<?> addNoticeWithLengthValidation(@RequestBody @Valid NoticeInput input,
                                                     Errors errors) {
        if(errors.hasErrors()) {
            List<ResponseError> responseErrors = new ArrayList<>();
            errors.getAllErrors().forEach(error -> {
                responseErrors.add(ResponseError.of((FieldError) error));
            });

            return new ResponseEntity<>(responseErrors, HttpStatus.BAD_REQUEST);
        }

        Notice notice = Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .hits(0)
                .likes(0)
                .regDate(now)
                .build();

        noticeRepository.save(notice);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/notice/latest/{count}")
    public Page<Notice> getNoticeLatest(@PathVariable int size) {
        return noticeRepository.findAll(PageRequest.of(0,size, Sort.Direction.DESC, "regDate"));
    }

    @ExceptionHandler(DuplicateNoticeException.class)
    public ResponseEntity<String> handlerNoticeDuplicateNoticeException(DuplicateNoticeException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.OK);
    }

    @PostMapping("/api/notice9")
    public ResponseEntity<?> addNoticeBlockDuplication(@RequestBody @Valid NoticeInput input) {

        // 중복 체크
        LocalDateTime checkDate = LocalDateTime.now().minusMinutes(1);

//        List<Notice> noticeList = noticeRepository.findByTitleAndContentsAndRegDateIsGreaterThanEqual(
//                input.getTitle(), input.getContents(), checkDate
//        );
//
//        if(!noticeList.isEmpty()){
//            throw new DuplicateNoticeException("1분이내에 등록된 동일한 공지사항이 존재합니다.");
//        }

        int count = noticeRepository.countByTitleAndContentsAndRegDateIsGreaterThanEqual(
                input.getTitle(), input.getContents(), checkDate
        );

        if(count>0){
            throw new DuplicateNoticeException("1분이내에 등록된 동일한 공지사항이 존재합니다.");
        }

        noticeRepository.save(Notice.builder()
                .title(input.getTitle())
                .contents(input.getContents())
                .hits(0)
                .likes(0)
                .regDate(now)
                .build());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
