package com.zy.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {
    //根据数据id查询子数据
    List<Dict> findChildData(long id);
    //导出数据字典接口
    void exportDictData(HttpServletResponse response);
    //导入数据字典数据
    void importDictData(MultipartFile file);
}
