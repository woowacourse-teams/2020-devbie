package underdogs.devbie.aws;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {
    //todo 내부모듈로 가야할듯
    private AmazonS3 s3Client;

    @Value("${cloud.aws.region.static:sample}")
    private String region;

    @Value("${cloud.aws.s3.bucket:sample}")
    private String bucket;

    @PostConstruct
    public void setS3Client() {
        s3Client = AmazonS3ClientBuilder.standard()
            .withRegion(this.region)
            .build();
    }

    public String upload(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        s3Client.putObject(new PutObjectRequest(bucket, fileName, file.getInputStream(), null)
            .withCannedAcl(CannedAccessControlList.PublicRead));
        return s3Client.getUrl(bucket, fileName).toString();
    }
}
