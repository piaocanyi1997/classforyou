package com.my.dao.test;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.my.dao.CategoryDAO;
import com.my.dao.LessonDAO;
import com.my.dao.StudentDAO;
import com.my.exception.AddException;
import com.my.exception.FindException;
import com.my.exception.ModifyException;
import com.my.vo.Lesson;
import com.my.vo.Student;

import lombok.extern.log4j.Log4j;
@RunWith(SpringJUnit4ClassRunner.class) //Juni4인 경우

//Spring 컨테이너용 XML파일 설정
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml", 
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class LessonDAOOracle {

	@Autowired
	private LessonDAO dao;
	@Autowired
	private StudentDAO sdao;
	@Autowired
	private CategoryDAO cdao;
	
	//@Test
	public void selectAll() throws FindException {
		List<Lesson> list = dao.selectAll();
		int expListSize = 2;
		assertEquals(expListSize, list.size());
	}
	
	//@Test
	public void selectById() throws FindException {
		int lessonId = 17;
		Lesson lesson =dao.selectById(lessonId);
		int expTeacherId =10;
		int expCategoryId = 302;
//		assertEquals(expTeacherId, lesson.getTeacher().getStudentId());
		assertEquals(expCategoryId, lesson.getLessonCategory());
//		assertEquals("연말,홈파티, 1분이면 만드는 칵테일", lesson.getLessonName());
		assertEquals(29, lesson.getDiffDays());
	}
	
	//@Test
	public void selectByUnion() throws FindException{
		String union = "음";
		List<Lesson> list = dao.selectByUnion(union);
		int expListSize = 1;
		assertEquals(expListSize, list.size());
	}

	//@Test
	public void insert() throws AddException, FindException, ParseException{
		Student student = sdao.selectById(20);
		String lesson_name =  "강좌신청 test";
		int lesson_target_amount =  100000;
		String from = "2021-06-16";
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		Date lesson_start_date = fm.parse(from);
		int lesson_fee =  10000;
		String lesson_description =  "한줄소개test";
		int lesson_category_id =  101;
		
		Lesson lesson = new Lesson(student, lesson_name,  lesson_target_amount,  lesson_start_date,
				lesson_fee,  lesson_description,  lesson_category_id);
		
		dao.insert(lesson);
	}
	 
	//@Test
	public void update() throws ModifyException,FindException, ParseException {
		Lesson lesson = dao.selectById(21);
		String lessonName =  "강좌신청 test3";
		String lessonDescription = "한줄소개test3";
		
		lesson.setLessonName(lessonName);
		lesson.setLessonDescription(lessonDescription);
		dao.update(lesson);
		
		int expCategoryId = 101;
		assertEquals(21, lesson.getLessonId());
		assertEquals(expCategoryId, lesson.getLessonCategory());
		assertEquals(lessonDescription, lesson.getLessonDescription());
	}
}
