package com.optimiza.clickbarber.controller

import com.optimiza.clickbarber.model.Resposta
import com.optimiza.clickbarber.model.RespostaUtils
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaAtualizarDto
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaDto
import com.optimiza.clickbarber.model.barbearia.dto.BarbeariaRespostaDto
import com.optimiza.clickbarber.service.BarbeariaService
import com.optimiza.clickbarber.service.S3Service
import com.optimiza.clickbarber.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*

@RestController
@RequestMapping("/barbearias")
class BarbeariaController @Autowired constructor(
    private val barbeariaService: BarbeariaService,
    private val s3Service: S3Service
) {

    @GetMapping
    fun buscarTodos(@RequestParam(name = "limit") limit: Int = 0): Resposta<List<BarbeariaDto>> {
        val pageable = if (limit == null || limit == 0) {
            Pageable.unpaged()
        } else {
            PageRequest.of(0, limit)
        }

        val barbeariasEncontradas = barbeariaService.buscarTodos(pageable)
        val mensagem = "${barbeariasEncontradas.size} ${Constants.Success.BARBEARIAS_ENCONTRADAS}"

        return RespostaUtils.ok(mensagem, barbeariasEncontradas)
    }

    @GetMapping("/nome")
    fun buscarPorNome(@RequestParam(name = "nome") nome: String): Resposta<List<BarbeariaDto>> {
        val barbearias = barbeariaService.buscarPorNome(nome)
        val mensagem = "${Constants.Success.BARBEARIAS_ENCONTRADAS_PELO_NOME} $nome"
        return RespostaUtils.ok(mensagem, barbearias)
    }

    @GetMapping("/idExterno")
    fun buscarPorIdExterno(@RequestParam(name = "idExterno") idExterno: UUID): Resposta<BarbeariaDto> {
        val barbeariaEncontrada = barbeariaService.buscarDtoPorIdExteno(idExterno)
        val mensagem = "${Constants.Success.BARBEARIAS_ENCONTRADA_PELO_ID_EXTERNO} $idExterno"
        return RespostaUtils.ok(mensagem, barbeariaEncontrada)
    }

    @PatchMapping
    fun atualizar(@RequestBody barbeariaAtualizarDto: BarbeariaAtualizarDto): Resposta<BarbeariaRespostaDto> {
        val barbeariaAtualizada = barbeariaService.atualizar(barbeariaAtualizarDto)
        return RespostaUtils.ok(Constants.Success.BARBEARIA_ATUALIZADA_SUCESSO, barbeariaAtualizada)
    }

    @PostMapping("/upload-logo")
    fun uploadLogo(@RequestParam("file") file: MultipartFile, idExterno: UUID): ResponseEntity<String> {
        val convertedFile = File(file.originalFilename!!)
        file.inputStream.use { inputStream ->
            convertedFile.outputStream().use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        val fileUrl = s3Service.uploadFile(file.originalFilename!!, convertedFile)

        convertedFile.delete()

        barbeariaService.atualizarLogoUrl(idExterno, fileUrl)

        return ResponseEntity.ok(fileUrl)
    }

}