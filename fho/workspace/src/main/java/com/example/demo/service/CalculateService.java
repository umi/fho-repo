package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BattleDTO;
import com.example.demo.repository.CalculateRepository;

@Service
public class CalculateService {

    @Autowired
    private CalculateRepository calculateRepository;

    public List<BattleDTO> clculateBattle() {
        return calculateRepository.battleStream();
    }
}
