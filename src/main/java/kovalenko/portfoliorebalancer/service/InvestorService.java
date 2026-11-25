package kovalenko.portfoliorebalancer.service;

import org.springframework.stereotype.Service;
import kovalenko.portfoliorebalancer.domain.Investor;
import kovalenko.portfoliorebalancer.dto.InvestorCreateRequest;
import kovalenko.portfoliorebalancer.dto.InvestorResponse;
import kovalenko.portfoliorebalancer.dto.InvestorUpdateRequest;
import kovalenko.portfoliorebalancer.exception.BusinessRuleException;
import kovalenko.portfoliorebalancer.exception.NotFoundException;
import kovalenko.portfoliorebalancer.repository.InvestorRepository;
import kovalenko.portfoliorebalancer.repository.PortfolioRepository;

import java.util.List;

@Service
public class InvestorService {

    private final InvestorRepository investorRepository;
    private final PortfolioRepository portfolioRepository;

    public InvestorService(InvestorRepository investorRepository,
                           PortfolioRepository portfolioRepository) {
        this.investorRepository = investorRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public List<InvestorResponse> findAll() {
        return investorRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public InvestorResponse findById(Long id) {
        Investor investor = investorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Investor " + id + " not found"));
        return toResponse(investor);
    }

    public InvestorResponse create(InvestorCreateRequest request) {
        Investor investor = new Investor(null, request.fullName());
        Investor saved = investorRepository.save(investor);
        return toResponse(saved);
    }

    public InvestorResponse update(Long id, InvestorUpdateRequest request) {
        Investor existing = investorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Investor " + id + " not found"));

        if (request.fullName() != null) {
            existing.setFullName(request.fullName());
        }

        Investor updated = investorRepository.update(id, existing)
                .orElseThrow(() -> new NotFoundException("Investor " + id + " not found"));
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!investorRepository.existsById(id)) {
            throw new NotFoundException("Investor " + id + " not found");
        }
        boolean hasPortfolios = !portfolioRepository.findByInvestorId(id).isEmpty();
        if (hasPortfolios) {
            throw new BusinessRuleException(
                    "Cannot delete investor " + id + " because they have existing portfolios");
        }
        investorRepository.deleteById(id);
    }

    private InvestorResponse toResponse(Investor investor) {
        return new InvestorResponse(investor.getId(), investor.getFullName());
    }
}