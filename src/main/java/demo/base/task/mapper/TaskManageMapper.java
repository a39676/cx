package demo.base.task.mapper;

import demo.base.task.pojo.param.mapperParam.UpdateIsOnParam;
import demo.base.task.pojo.param.mapperParam.UpdateRunResultParam;
import demo.base.task.pojo.po.TaskManage;

public interface TaskManageMapper {
    int insert(TaskManage record);

    int insertSelective(TaskManage record);
    
    TaskManage findTask(Long id);
    
    int updateRunResult(UpdateRunResultParam param);
    
    int updateIsOn(UpdateIsOnParam param);
}