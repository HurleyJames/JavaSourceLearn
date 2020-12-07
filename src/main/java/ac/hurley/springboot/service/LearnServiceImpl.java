package ac.hurley.springboot.service;

import ac.hurley.springboot.dao.LearnMapper;
import ac.hurley.springboot.model.LearnResource;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LearnServiceImpl implements LearnService {

    @Autowired
    LearnMapper learnMapper;

    @Override
    public int add(LearnResource learnResource) {
        return this.learnMapper.add(learnResource);
    }

    @Override
    public int update(LearnResource learnResource) {
        return this.learnMapper.update(learnResource);
    }

    @Override
    public int deleteByIds(String[] ids) {
        return this.learnMapper.deleteByIds(ids);
    }

    @Override
    public LearnResource queryLearnResourceById(Long id) {
        return this.learnMapper.queryLearnResourceById(id);
    }

    @Override
    public List<LearnResource> queryLearnResourceList(Map<String, Object> params) {
        // 使用PageHelper进行分页处理, PageHelper.startPage(int pageNum, int pageSize)
        // pageNum显示是第几页，pageSize显示是每页显示多少条
        PageHelper.startPage(Integer.parseInt(params.get("page").toString()),
                Integer.parseInt(params.get("rows").toString()));
        return this.learnMapper.queryLearnResourceList(params);
    }
}
