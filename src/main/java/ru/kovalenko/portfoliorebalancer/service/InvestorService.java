package ru.kovalenko.portfoliorebalancer.service;

import org.springframework.stereotype.Service;
import ru.kovalenko.portfoliorebalancer.domain.Investor;
import ru.kovalenko.portfoliorebalancer.repository.InvestorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;

    public InvestorService(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    public List<Investor> findAll() {
        return investorRepository.findAll();
    }

    public Optional<Investor> findById(Long id) {
        return investorRepository.findById(id);
    }
}