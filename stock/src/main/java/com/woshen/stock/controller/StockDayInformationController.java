package com.woshen.stock.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woshen.common.webcommon.db.action.AbstractController;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.entity.StockDayInformation;
import com.woshen.stock.server.IStockDayInformationService;
import com.woshen.stock.server.IStockService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 股票每日数据表 前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Controller
@RequestMapping("stockDayInformation")
public class StockDayInformationController extends AbstractController<Integer, StockDayInformation> {

    @Resource
    private IStockDayInformationService stockDayInformationServiceImpl;

    @Resource
    private IStockService stockServiceImpl;

    @Override
    public IStockDayInformationService getService() {
    return stockDayInformationServiceImpl;
    }

    @Override
    public Map<String, ?> afterList(HttpServletRequest request, HttpServletResponse response, StockDayInformation queryData, Page<StockDayInformation> pageData) {
     List<StockDayInformation> records = pageData.getRecords();
     if(!CollectionUtils.isEmpty(records)){
      List<String> collect = records.stream().map(StockDayInformation::getCode).distinct().collect(Collectors.toList());
      QueryWrapper<Stock> baseWrapper = stockServiceImpl.getBaseWrapper(new Stock());
      baseWrapper.in("code",collect);
      List<Stock> list = stockServiceImpl.list(baseWrapper);
      Map<String, String> map = list.stream().collect(Collectors.toMap(Stock::getCode, Stock::getName));
      records.stream().forEach(t-> t.setName(map.get(t.getCode())));
     }
     return super.afterList(request, response, queryData, pageData);
    }
}
