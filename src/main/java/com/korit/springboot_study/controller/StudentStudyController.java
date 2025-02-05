package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.study.ReqAddInstructorDto;
import com.korit.springboot_study.dto.request.study.ReqAddMajorDto;
import com.korit.springboot_study.dto.response.common.ResponseDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.mapper.StudentStudyMapper;
import com.korit.springboot_study.service.StudentStudyService;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentStudyController {

    @Autowired
    private StudentStudyService studentStudyService;

    @GetMapping("/api/study/majors")
    @ApiOperation(value = "학과 전체 조회")
    public ResponseEntity<SuccessResponseDto<List<Major>>> getMajors() throws NotFoundException {
        return ResponseEntity.ok().body(studentStudyService.getMajorsAll());
    }

    @GetMapping("/api/study/instructor")
    @ApiOperation(value = "교수 전체 조회")
    public ResponseEntity<SuccessResponseDto<List<Instructor>>> getInstructor() throws NotFoundException {
        return ResponseEntity.ok().body(studentStudyService.getInstructorsAll());
    }

    @PostMapping("/api/study/major")
    public ResponseEntity<SuccessResponseDto<?>> addMajor(@RequestBody ReqAddMajorDto reqAddMajorDto) {
        System.out.println(reqAddMajorDto);
        return ResponseEntity.ok().body(studentStudyService.addMajor(reqAddMajorDto));
    }

    @PostMapping("/api/study/instructor")
    public ResponseEntity<SuccessResponseDto<?>> addInstructor(@RequestBody ReqAddInstructorDto reqAddInstructorDto) {
        System.out.println(reqAddInstructorDto);
        return ResponseEntity.ok().body(studentStudyService.addInstructor(reqAddInstructorDto));
    }
}

/*
학생 학습(StudentStudy) 관련 기능을 제공하는 API

StudentStudyController (컨트롤러)
StudentStudyService (서비스)
ResponseDto, NotFoundResponseDto, SuccessResponseDto (응답 DTO)
GlobalRestControllerAdvice (전역 예외 처리)

요청 흐름 예상
클라이언트가 StudentStudyController에 HTTP 요청을 보냄.
StudentStudyController가 요청을 StudentStudyService에 전달.
StudentStudyService가 비즈니스 로직을 수행.
결과 데이터를 ResponseDto 또는 SuccessResponseDto, NotFoundResponseDto로 반환.
만약 예외 발생 시 GlobalRestControllerAdvice가 처리.


Spring Boot + MyBatis 코드 작성 흐름 (8단계)
엔티티(Entity) 작성

데이터베이스 테이블(major_tb, instructor_tb)을 매핑하는 Java 클래스를 작성함.
Major, Instructor 등의 클래스를 만들고, 필드와 getter/setter를 정의함.
MyBatis 매퍼 인터페이스 작성

StudentStudyMapper 인터페이스를 만들고, 데이터 조회 메서드를 선언함.
예: selectMajorsAll(), selectInstructorAll() 등의 메서드 정의.
MyBatis 매퍼 XML 작성

student_study_mapper.xml에서 SQL 쿼리와 resultMap을 정의하여 Java 객체로 매핑함.
selectMajorsAll, selectInstructorAll SQL 쿼리를 작성함.
레포지토리(Repository) 클래스 작성

StudentStudyRepository를 생성하여 StudentStudyMapper를 활용한 데이터 조회 메서드를 작성함.
Optional을 사용하여 데이터가 없을 경우 예외 처리가 가능하도록 함.
서비스(Service) 클래스 작성

StudentStudyService를 작성하여 컨트롤러에서 받은 요청을 처리함.
StudentStudyRepository에서 데이터를 조회하고, SuccessResponseDto 또는 NotFoundException을 반환함.
컨트롤러(Controller) 작성

StudentStudyController를 작성하여 HTTP 요청을 처리하고, StudentStudyService를 호출함.
클라이언트 요청을 받아 DTO 형식으로 응답을 반환함.
전역 예외 처리(Global Exception Handling) 작성

GlobalRestControllerAdvice에서 @ExceptionHandler를 이용하여 NotFoundException 등의 예외를 일괄 처리함.
예외 발생 시 NotFoundResponseDto를 반환하도록 설정함.
Spring Boot 실행 및 테스트

서버를 실행하고, API 테스트 툴(Postman, Swagger 등)로 GET /majors, GET /instructors API를 호출하여 데이터 조회를 확인함.
예외 발생 시 GlobalRestControllerAdvice가 정상적으로 처리하는지 확인함.
*/