package org.example.thymeleafdemo2.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/")
    public String home(Model model) {
        List<Map<String, Object>>  tables = jdbcTemplate.queryForList(
                "SELECT name, hits FROM test "
        );
        model.addAttribute("tables", tables);
        return "index";
    }
    @PostMapping("/add")
    public String addName(@RequestParam("name") String name, @RequestParam("hits") Integer hits) {
        jdbcTemplate.update("INSERT INTO test (name,hits) VALUES (?,?)", name,hits);
        return "redirect:/";
    }
    @PostMapping("/delete")
    public String deleteName(@RequestParam("name") String name) {
        jdbcTemplate.update("DELETE FROM test WHERE name = ?", name);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String updateName(
            @RequestParam("oldName") String oldName,
            @RequestParam("name") String name,
            @RequestParam("hits") Integer hits
    ) {
        jdbcTemplate.update("UPDATE test SET name = ?, hits = ? WHERE name = ?", name, hits, oldName);
        return "redirect:/";
    }
    @GetMapping("/update2")
    public String updateName2(
            @RequestParam("name") String name,
            @RequestParam("hits") Integer hits) {
        return "update";
    }
}
