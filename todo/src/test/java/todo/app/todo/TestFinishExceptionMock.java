package todo.app.todo;

import java.util.Collection;

import org.terasoluna.gfw.common.exception.BusinessException;
import org.terasoluna.gfw.common.message.ResultMessage;
import org.terasoluna.gfw.common.message.ResultMessages;

import todo.domain.model.Todo;
import todo.domain.service.todo.TodoService;

public class TestFinishExceptionMock implements TodoService{

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

	//mockを作成(exceptionを返す)
	@Override
	public Todo finish(String todoId) {
		// TODO 自動生成されたメソッド・スタブ
		ResultMessages messages = ResultMessages.error();
		messages.add(ResultMessage.fromText("[E004]The requested Todo is not found. (id=cceae402-c5b1-440f-bae2-7bee19dc17fb)"));
		
		throw new BusinessException(messages);
	}

	@Override
	public void delete(String todoId) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	
}
