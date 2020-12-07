package ac.hurley.springboot.service;

import ac.hurley.springboot.model.LearnResource;

import java.util.List;
import java.util.Map;

/**
 * 定义和Learn有关的操作的接口方法
 */
public interface LearnService {
    /**
     * 添加
     *
     * @param learnResource
     * @return
     */
    int add(LearnResource learnResource);

    /**
     * 更新
     *
     * @param learnResource
     * @return
     */
    int update(LearnResource learnResource);

    /**
     * 根据id删除
     *
     * @param ids
     * @return
     */
    int deleteByIds(String[] ids);

    /**
     * 根据id查询
     *
     * @param learnResource
     * @return
     */
    LearnResource queryLearnResourceById(Long learnResource);

    /**
     * 查询所有的LearnResource列表
     *
     * @param params
     * @return
     */
    List<LearnResource> queryLearnResourceList(Map<String, Object> params);
}
