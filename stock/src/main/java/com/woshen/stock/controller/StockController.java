package com.woshen.stock.controller;


import com.woshen.common.webcommon.db.action.AbstractController;
import com.woshen.stock.entity.Stock;
import com.woshen.stock.server.IStockService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;

/**
 * <p>
 * 股票表 前端控制器
 * </p>
 *
 * @author liuhaibo
 * @since 2023-04-01
 */
@Controller
@RequestMapping("stock")
public class StockController extends AbstractController<Integer, Stock> {

 @Resource
 private IStockService stockServiceImpl;

 @Override
 public IStockService getService() {
 return stockServiceImpl;
 }

}
