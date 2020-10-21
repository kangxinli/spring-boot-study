package com.sample.excel.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.excel.service.ExportService;

@RestController
public class ExportController {
	
	@Autowired
	private ExportService exportService;

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        this.exportService.export(response);
    }

}
