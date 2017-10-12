package todo.app.todo;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

public class TestFinishMock implements TodoService{

	@Override
	public Collection<Todo> findAll() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Todo create(Todo todo) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	//mockを作成
	@Override
	public Todo finish(String todoId) {
		// TODO 自動生成されたメソッド・スタブ
		
		Todo todo = new Todo();
		try {
			todo = getTodoTrueData();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return todo;
	}

	@Override
	public void delete(String todoId) {
		// TODO 自動生成されたメソッド・スタブ
		
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
}
