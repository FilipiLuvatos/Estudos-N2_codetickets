package com.estudos.codetickets.config;

import com.estudos.codetickets.Importacao;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ImportacaoJobConfiguration {

    @Autowired
    private PlatformTransactionManager transactionManager;

    //jobRepository irá monitorar o estado da execução
    @Bean
    public Job job(Step passoInicial,  JobRepository jobRepository){
        return new JobBuilder("geracao-tickets", jobRepository) //Nome
                .start(passoInicial)//Step inical
                .incrementer(new RunIdIncrementer()) // Irá gerar logs
                .build();
    }
    @Bean //Para ser gerenciado pelo spring
    public Step passoInicial(ItemReader<Importacao> reader, ItemWriter<Importacao> writer, JobRepository jobRepository){
        return new StepBuilder("passo-inicial", jobRepository)
                .<Importacao,Importacao>chunk(200, transactionManager)// quantos dados serão processados por bloco
                .reader(reader) //ler
                .writer(writer) //escrever
                .build();
    }

    @Bean
    public ItemReader<Importacao> reader() {
        return new FlatFileItemReaderBuilder<Importacao>()
                .name("leitura-csv")
                .resource(new FileSystemResource("files/dados.csv")) //local do arquivo
                .comments("--")//Linhas que tem "--" seria comentário
                .delimited()
                .names("cpf", "cliente", "nascimento", "evento", "data", "tipoIngresso", "valor")
                .targetType(Importacao.class)
                .build();
    }
}
