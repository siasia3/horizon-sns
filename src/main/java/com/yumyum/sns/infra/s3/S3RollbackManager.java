package com.yumyum.sns.infra.s3;

import com.yumyum.sns.attachment.service.AttachmentService;
import io.awspring.cloud.s3.S3Operations;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Component
@RequiredArgsConstructor
public class S3RollbackManager {

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;
    private final S3Operations s3Operations;

    //트랜잭션 롤백시 업로드 된 s3 파일 삭제
    public void deleteIfTransactionRollback(List<String> fileNames) {
        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        if (status == STATUS_ROLLED_BACK) {
                            fileNames.stream()
                                    .forEach(savedFileName -> s3Operations.deleteObject(bucket, savedFileName));
                        }
                    }
                }
        );
    }
}
