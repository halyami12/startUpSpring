package todo.domain.repository.todo;

import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvURLDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

//AbstractDataSetLoaderクラスを継承する
public class CsvDataSetLoader extends AbstractDataSetLoader {

	//標準のxml形式からcsv形式のフォーマットを読み込む設定に変更（override）する
	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		// TODO 自動生成されたメソッド・スタブ
		return new CsvURLDataSet(resource.getURL());
	}
}
