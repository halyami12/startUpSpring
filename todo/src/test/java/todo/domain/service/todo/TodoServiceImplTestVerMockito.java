package todo.domain.service.todo;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.terasoluna.gfw.common.exception.BusinessException;

import todo.domain.model.Todo;
import todo.domain.repository.todo.TodoRepository;

/**
 * Service Test
 * mockitoによるRepositoryメソッドのモック化
 * 
 */
public class TodoServiceImplTestVerMockito {

	@Rule
	public MockitoRule mockito = MockitoJUnit.rule();
	
	@InjectMocks
	TodoServiceImpl target;
	
	@Mock
	TodoRepository todoRepository;
	
	@Before
	public void setUp() throws Exception{
		when(todoRepository.update(anyObject())).thenReturn(true);
	}
	
	//正常に動作したパターン
	@Test
	public void testFinishOK() throws Exception{
		
		//todoのfinishedにfalseが格納されているデータを返す
		when(todoRepository.findOne(anyString())).thenReturn(getTodoFalseData());
		
		String todoId = "cceae402-c5b1-440f-bae2-7bee19dc17fb";
		
		Todo todo = target.finish(todoId);
		assertTodo(todo);
		
	}
	
	//取得したTodoオブジェクトのfinishedが既にtrueで異常が発生したパターン
	@Test(expected = BusinessException.class)
	public void testFinishNG() throws Exception{
		
		//todoのfinishedにtrueが格納されているデータを返す
		when(todoRepository.findOne(anyString())).thenReturn(getTodoTrueData());
		
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
	
	//モック用の戻り値データを作成
	public Todo getTodoFalseData() throws Exception {
		
		Todo todo = new Todo();
		todo.setTodoId("cceae402-c5b1-440f-bae2-7bee19dc17fb");
		todo.setTodoTitle("one");
		todo.setFinished(false);
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String strDate = "2017-10-01 15:39:17.888";
		Date date1 = sdFormat.parse(strDate);
		todo.setCreatedAt(date1);
		
		return todo;
	}
	
	//モック用の戻り値データを作成
	public Todo getTodoTrueData() throws Exception {
		
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
	
	//データ検証用メソッド
	//期待値はgetTodoTrueData()にて作成したデータ
	//検証対象はtarget.finish(todoId)から取得したデータ
	public void assertTodo(Todo todo) throws Exception{
		
		Todo expectTodo = getTodoTrueData();
		
		assertEquals(expectTodo.getTodoId(), todo.getTodoId());
		assertEquals(expectTodo.getTodoTitle(), todo.getTodoTitle());
		assertEquals(expectTodo.isFinished(), todo.isFinished());
		assertEquals(expectTodo.getCreatedAt(), todo.getCreatedAt());
		
	}
}
