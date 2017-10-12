package todo.domain.service.todo;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.exception.BusinessException;

import todo.domain.model.Todo;

/**
 * Service Test
 * Repositoryは前提として品質担保済みの想定で実施している
 * データのセットアップはJdbcTemplateを使用した
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context.xml"})
@Transactional
public class TodoServiceImplTest {

	@Inject
	TodoService target;
	
	@Before
	public void setUp() throws Exception {
		//@sqlアノテーションで指定したsqlファイルによってセットアップを実行するため、処理なし
	}
	
	//正常に動作したパターン
	@Test
	@Rollback
	@Sql("classpath:database/test_data_testFinishOK.sql")
	public void testFinishOK() throws Exception{
		
		String todoId = "cceae402-c5b1-440f-bae2-7bee19dc17fb";
		
		Todo todo = target.finish(todoId);
		assertTodo(todo);
		
	}
	
	//取得したTodoオブジェクトのfinishedが既にtrueで異常が発生したパターン
	@Test(expected = BusinessException.class)
	@Rollback
	@Sql("classpath:database/test_data_testFinishNG.sql")
	public void testFinishNG() throws Exception{
		
		String todoId = "cceae402-c5b1-440f-bae2-7bee19dc17fb";
		
		//try-catch文はなくてもJunitとしては正常になるが、printStackTraceメソッドでエラーの内容を表示させている。
		try {
			target.finish(todoId);
		}catch (BusinessException e) {
			// TODO: handle exception
			e.printStackTrace();
			
			throw e;
		}
	}
	
	//データ検証用メソッド
	//期待値はgetTodoData()にて作成したデータ
	//検証対象はtarget.finish(todoId)から取得したデータ
	public void assertTodo(Todo todo) throws Exception{
		
		Todo expectTodo = getTodoData();
		
		assertEquals(expectTodo.getTodoId(), todo.getTodoId());
		assertEquals(expectTodo.getTodoTitle(), todo.getTodoTitle());
		assertEquals(expectTodo.isFinished(), todo.isFinished());
		assertEquals(expectTodo.getCreatedAt(), todo.getCreatedAt());
		
	}
	
	//モック用の戻り値データを作成
	public Todo getTodoData() throws Exception {
		
		Todo todo = new Todo();
		todo.setTodoId("cceae402-c5b1-440f-bae2-7bee19dc17fb");
		todo.setTodoTitle("one");
		todo.setFinished(true);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strDate = "2017-10-01 15:39:17.888";
		Date date1 = sdFormat.parse(strDate);
		todo.setCreatedAt(date1);
		
		return todo;
	}
}
