package Example.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import Example.entity.Department;
import Example.repository.DepartmentRepo;


@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private DepartmentRepo departmentRepo;

    @Bean
    public FlatFileItemReader<Department> reader() {
        FlatFileItemReader<Department> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new ClassPathResource("DepartmentDetails.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1); // Skip header line
        itemReader.setLineMapper(lineMapper());
        
        // Set reader to non-strict mode
        itemReader.setStrict(false);

        return itemReader;
    }


    private LineMapper<Department> lineMapper() {
        DefaultLineMapper<Department> lineMapper = new DefaultLineMapper<>(); // extract values from the csv file

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "DepartmentName", "managerId", "managerName");
        BeanWrapperFieldSetMapper<Department> fieldSetMapper = new BeanWrapperFieldSetMapper<>(); // it will map from target class which is employee
        fieldSetMapper.setTargetType(Department.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public DepartmentProcessor processor() {
        return new DepartmentProcessor();
    }

    @Bean
    public RepositoryItemWriter<Department> writer() {
        RepositoryItemWriter<Department> writer = new RepositoryItemWriter<>();
        writer.setRepository(departmentRepo);
        writer.setMethodName("save");
        return writer;
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("csv-step").<Department, Department>chunk(1)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob() {
        return jobBuilderFactory.get("importDepartments")
                .flow(step1()).end().build();
    }
}
