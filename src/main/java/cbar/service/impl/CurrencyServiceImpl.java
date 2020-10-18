package cbar.service.impl;

import cbar.model.Valute;
import cbar.repository.CurrencyRepository;
import cbar.repository.impl.CurrencyRepositoryImpl;
import cbar.service.CurrencyService;

import java.util.List;

public class CurrencyServiceImpl  implements CurrencyService {

    private CurrencyRepository repository = new CurrencyRepositoryImpl();

    @Override
    public Valute saveValute(Valute valute) {
        return repository.saveValute(valute);
    }

    @Override
    public boolean saveBatchValutes(List<Valute> valuteList) {
        return repository.saveBatchValutes(valuteList);
    }

    @Override
    public Valute updateValute(Valute valute) {
        return repository.updateValute(valute);
    }

    @Override
    public boolean removeValute(int id) {
        return repository.removeValute(id);
    }

    @Override
    public Valute findById(int id) {
        return repository.findById(id);
    }

    @Override
    public Valute findByCode(String code) {
        return repository.findByCode(code);
    }

    @Override
    public List<Valute> findValutes() {
        return repository.findValutes();
    }
}
