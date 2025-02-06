package com.korit.springboot_study.controller;

import com.korit.springboot_study.dto.request.study.ReqAddInstructorDto;
import com.korit.springboot_study.dto.request.study.ReqAddMajorDto;
import com.korit.springboot_study.dto.request.study.ReqUpdateMajorDto;
import com.korit.springboot_study.dto.response.common.SuccessResponseDto;
import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.service.StudentStudyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.regex.Pattern;

@RestController
@Validated // 컨트롤러의 매개변수에서 바로 유효성 검사할 때 @Valid와는 상관없음 / @Min(value = 1, message = "학과 ID는 1이상의 정수여야 합니다.")이런거 쓸려고 함
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
    public ResponseEntity<SuccessResponseDto<Major>> addMajor(@Valid @RequestBody ReqAddMajorDto reqAddMajorDto) throws MethodArgumentNotValidException {
        System.out.println(reqAddMajorDto);
        return ResponseEntity.ok().body(studentStudyService.addMajor(reqAddMajorDto));

//        boolean isNull = reqAddMajorDto == null;
//        boolean isBlank = reqAddMajorDto.getMajorName().isBlank();
//        boolean isKor = !Pattern.matches("^[ㄱ-ㅎ|가-힣]*$", reqAddMajorDto.getMajorName());
//
//        if(isNull || isBlank || isKor) {
//            BindingResult bindingResult = new BeanPropertyBindingResult(null, "major");
//            throw new MethodArgumentNotValidException(null, bindingResult);
//        }

    }

    @PostMapping("/api/study/instructor")
    public ResponseEntity<SuccessResponseDto<Instructor>> addInstructor(@RequestBody ReqAddInstructorDto reqAddInstructorDto) {
        System.out.println(reqAddInstructorDto);
        return ResponseEntity.ok().body(studentStudyService.addInstructor(reqAddInstructorDto));
    }

    @PutMapping("/api/study/major/{majorId}") // 1. put요청이라서 {majorId}JSON형식으로 주면
    public ResponseEntity<SuccessResponseDto<?>> updateMajor(
            @ApiParam(value = "학과 ID", example = "1", required = true)
            @PathVariable @Min(value = 1, message = "학과 ID는 1이상의 정수여야 합니다.") int majorId,
            @Valid @RequestBody ReqUpdateMajorDto reqUpdateMajorDto) throws MethodArgumentNotValidException, NotFoundException {// 2. DTO형식으로 받기 위해 @RequestBody를 씀

        return ResponseEntity.ok().body(studentStudyService.modifyMajor(majorId, reqUpdateMajorDto));
    }
}

/*

흐름 순서: 서비스 -> Repository -> Mapper -> MySQL
부품부터 만들기: 컨트롤러, DB_tb -> Mapper -> Repository -> 서비스

@ApiOperation:
    value: API 메서드의 간단한 설명. 예를 들어, "학과 전체 조회"처럼 메서드가 하는 일을 간략히 설명합니다.
    notes: API 메서드에 대한 상세한 설명. 예를 들어, 메서드가 어떤 데이터를 반환하는지, 또는 특정 동작을 수행하는지에 대한 추가적인 설명을 제공할 수 있습니다.

@Valid: ReqUpdateMajorDto와 같은 DTO 객체에서 유효성 검사를 실행할 때 사용합니다.
@RequestBody: 클라이언트가 { "majorName": "정보 보안" } 같은 JSON형식으로 주면 spring에서 DTO형식으로 받기 위해서 씀
@Min: 최소값이 1이며 1이 아니면 유효성 검사 실패.
@PathVariable: {majorId}와 같은 동적변수를 바인딩하고 메서드에서 사용하기 위해서 씀

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