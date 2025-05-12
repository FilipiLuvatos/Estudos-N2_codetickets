package com.estudos.codetickets;

import org.springframework.batch.item.ItemProcessor;

public class ImportacaoProcessor implements ItemProcessor<Importacao,Importacao> {


    @Override
    public Importacao process(Importacao item) throws Exception {
        if(item.getTipoIngresso().equalsIgnoreCase("vip")){
            item.setTaxaAdm(130.00);
        }else if(item.getTipoIngresso().equalsIgnoreCase("vip")){
            item.setTaxaAdm(80.00);
        }else   item.setTaxaAdm(50.00);
        return item;
    }
}
