package com.optimiza.clickbarber.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.S3Object
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File

@Service
class S3Service(
    @Value("\${aws.s3.bucketName}") private val bucketName: String
) {

    private val s3Client: AmazonS3 = AmazonS3ClientBuilder.standard().build()

    fun uploadFile(keyName: String, file: File): String {
        s3Client.putObject(PutObjectRequest(bucketName, keyName, file))
        return s3Client.getUrl(bucketName, keyName).toString()
    }

    fun downloadFile(bucketName: String, keyName: String): S3Object {
        return s3Client.getObject(bucketName, keyName)
    }

}