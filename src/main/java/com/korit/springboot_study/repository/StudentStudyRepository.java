package com.korit.springboot_study.repository;

import com.korit.springboot_study.entity.study.Instructor;
import com.korit.springboot_study.entity.study.Major;
import com.korit.springboot_study.exception.CustomDuplicateKeyException;
import com.korit.springboot_study.mapper.StudentStudyMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class StudentStudyRepository {

    @Autowired
    private StudentStudyMapper studentStudyMapper;

    public Optional<List<Major>> findMajorAll() {
        List<Major> foundMajors = studentStudyMapper.selectMajorsAll();

        // if문을 사용
//        if (foundMajors.isEmpty()) {
//            return Optional.empty();
//        }
        // 삼항연산자를 사용
        return foundMajors.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundMajors);
    }

    public Optional<List<Instructor>> findInstructorAll() {
        List<Instructor> foundInstructor = studentStudyMapper.selectInstructorAll();

        return foundInstructor.isEmpty()
                ? Optional.empty()
                : Optional.ofNullable(foundInstructor);
    }

    public Optional<Major> saveMajor(Major major) throws DuplicateKeyException {
        try {
            studentStudyMapper.insertMajor(major); // 성공횟수
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("majorName", "이미 존재하는 학과명입니다.")
            );
        }
        return Optional.ofNullable(new Major(major.getMajorId(), major.getMajorName()));
    }

    public Optional<Instructor> saveInstructor(Instructor instructor) throws DuplicateKeyException {
        try {
            studentStudyMapper.insertInstructor(instructor);
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                    e.getMessage(),
                    Map.of("instructorName", "이미 존재하는 교수명입니다.")
            );
        }
        return Optional.ofNullable(new Instructor(instructor.getInstructorId(), instructor.getInstructorName()));
    }

    public Optional<Major> updateMajor(Major major) throws NotFoundException, DuplicateKeyException {
        try {
            if(studentStudyMapper.updateMajorName(major) == 0) {
                return Optional.empty();
            }
        } catch (DuplicateKeyException e) {
            throw new CustomDuplicateKeyException(
                e.getMessage(),
                Map.of("majorName", "이미 존재하는 학과명입니다.")
            );
        }
        return Optional.ofNullable(major);
    }
}





/*
✔ Optional<List<Major>>를 사용하면 null을 방지하여 NPE 위험을 줄일 수 있음.
✔ API 사용자가 명확한 예외 처리를 할 수 있도록 유도 가능.
✔ 가독성이 좋은 코드 작성이 가능하며, 메서드 체이닝을 활용할 수 있음.

✅ 즉, Optional<List<Major>>는 null을 안전하게 처리하고, 예외 발생을 줄이기 위해 사용하는 것입니다!
*/
