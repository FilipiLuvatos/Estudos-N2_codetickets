package com.estudos.codetickets;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.net.BindException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//Classe criada para converter os dados
//recebidos de String para os campos corretos

public class ImportacaoMapper implements FieldSetMapper<Importacao> {
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    //Formas que quero configurar as datas

    @Override
    public Importacao mapFieldSet(FieldSet fieldSet) {
        Importacao importacao = new Importacao();
        importacao.setCpf(fieldSet.readString("cpf")); //Lê em string e seta
        importacao.setCliente(fieldSet.readString("cliente"));
        importacao.setNascimento(LocalDate.parse(fieldSet.readString("nascimento"), dateFormatter)); //Lê em string e converte para o formato correto
        importacao.setEvento(fieldSet.readString("evento"));
        importacao.setData(LocalDate.parse(fieldSet.readString("data"), dateFormatter));
        importacao.setTipoIngresso(fieldSet.readString("tipoIngresso"));
        importacao.setValor(fieldSet.readDouble("valor"));
        importacao.setHoraImportacao(LocalDateTime.now());
        return importacao;
    }
}