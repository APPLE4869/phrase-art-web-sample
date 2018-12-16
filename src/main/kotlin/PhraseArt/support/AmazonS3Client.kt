package PhraseArt.support

import PhraseArt.domain.support.AbstractId
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.apache.tomcat.util.http.fileupload.IOUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.InputStream
import java.util.*

@Component
class S3Client {
    @Value("\${aws.credentials.accessKey}")
    val accessKey: String = ""

    @Value("\${aws.credentials.secretKey}")
    val secretKey: String = ""

    @Value("\${aws.bucket_name}")
    val bucketName: String = ""

    var amazonS3Client: AmazonS3Client? = null

    // TODO : できれば画像圧縮の処理を書くかLambdaなどで圧縮の仕組みを作る。(ひとまずはアプリ側で圧縮してから送信する仕様で逃れる。)
    fun upload(file: MultipartFile, symbol: String, id: AbstractId?): String {
        val filePath = makeFilePath(symbol, id)
        val inputStream: InputStream = file.getInputStream()
        val putObjectRequest = makePutObjectRequest(filePath, inputStream)

        // アップロード
        val putObjectResult: PutObjectResult = amazonS3Client().putObject(putObjectRequest)

        IOUtils.closeQuietly(inputStream)

        return filePath;
    }

    // @Valueが遅延評価のような挙動らしく、インスタンス生成したタイミングでは値が設定されていないため、
    // 仕方なくamazonS3Clientをメソッドで格納 & 取得するようにしている。
    private fun amazonS3Client(): AmazonS3Client {
        if (amazonS3Client != null)
            return amazonS3Client as AmazonS3Client

        val basicAWSCredentials: BasicAWSCredentials = BasicAWSCredentials(accessKey, secretKey)
        this.amazonS3Client = AmazonS3Client(basicAWSCredentials)
        return amazonS3Client as AmazonS3Client
    }

    private fun makeFilePath(symbol: String, id: AbstractId?): String {
        val imageName = "${UUID.randomUUID().toString().substring(0, 8)}.jpg" // 画像名はランダム文字列
        val idPart = id?.let { "${it.value}/" } ?: ""

        return "${symbol}/${idPart}${imageName}"
    }

    private fun makePutObjectRequest(filePath: String, inputStream: InputStream): PutObjectRequest {
        val putObjectRequest: PutObjectRequest = PutObjectRequest(bucketName, filePath, inputStream, ObjectMetadata());
        putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead)
        return putObjectRequest
    }
}
