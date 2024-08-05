import React, { useEffect, useState } from "react";
import { Button, Col, Container, Form, Row, Toast, ToastContainer } from "react-bootstrap";
import { BarbeariaService } from "../../services/BarbeariaService";
import { Barbearia, BarbeariaAtualizarDto } from "../../models/Barbearia";
import { Loading } from "../../components/Loading";
import { MdCheck, MdError } from "react-icons/md";
import { HorarioFuncionamento } from "../../models/HorarioFuncionamento";
import { HorarioFuncionamentoForm } from "../../components/HorarioFuncionamentoForm";
import { DiaDaSemana, mapDiaDaSemana } from "../../models/DiaDaSemana";


export const ConfiguracoesPerfil: React.FC = () => {
    const idBarbearia: string = localStorage.getItem('idBarbearia') as string;
    const [_, setBarbearia] = useState<Barbearia | null>(null);
    const [errorMessage, setErrorMessage] = useState<string>('');
    const [loading, setLoading] = useState<boolean>(true);
    const [loadingAtualizacao, setLoadingAtualizacao] = useState<boolean>(false);
    const [disabled, setDisabled] = useState<boolean>(true);
    const [disabledHorarioFuncionamento, setDisabledHorarioFuncionamento] = useState<boolean>(true);

    const [nome, setNome] = useState<string>('');
    const [cnpj, setCnpj] = useState<string>('');
    const [endereco, setEndereco] = useState<string>('');
    const [telefone, setTelefone] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [horarios, setHorarios] = useState<HorarioFuncionamento[]>([]);
    const [showToastSuccess, setShowToastSuccess] = useState<boolean>(false);
    const [showToastError, setShowToastError] = useState<boolean>(false);
    const [messageToast, setMessageToast] = useState<string>('');

    useEffect(() => {
        fetchBarbearia();
    }, [])

    const fetchBarbearia = async () => {
        try {
            const barbeariaEncontrada: Barbearia = await BarbeariaService.buscarInformacoesDaBarbearia();
            setBarbearia(barbeariaEncontrada);

            setNome(barbeariaEncontrada.nome);
            setCnpj(barbeariaEncontrada.cnpj);
            setEndereco(barbeariaEncontrada.endereco);
            setTelefone(barbeariaEncontrada.telefone);
            setEmail(barbeariaEncontrada.email);
            setHorarios(barbeariaEncontrada.horarios);
        } catch (error) {
            setErrorMessage(`Erro ao buscar Barbearia: ${error}`);
            setMessageToast(errorMessage as string);
            setShowToastError(true);
        } finally {
            setLoading(false);
        }
    }

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        setLoadingAtualizacao(true);

        const barbeariaAtualizar: BarbeariaAtualizarDto = {
            idExterno: idBarbearia,
            nome: nome,
            cnpj: cnpj,
            endereco: endereco,
            telefone: telefone,
            email: email
        }

        try {            
            const barbeariaAtualizada = await BarbeariaService.atualizarBarbearia(barbeariaAtualizar)
            if (barbeariaAtualizada) {
                setMessageToast('Barbearia atualizada com sucesso!');
                setShowToastSuccess(true);
                setDisabled(true);
            }
        } catch (error) {            
            setErrorMessage('Erro ao tentar atualizar barbearia.');
            setMessageToast(errorMessage as string);
            setShowToastError(true);
            setLoadingAtualizacao(false);
        } finally {
            setLoadingAtualizacao(false);
        }
    }

    if (loading) {
        return (
            <Loading message="Buscando informações da Barbearia..." />
        );
    }

    if (loadingAtualizacao) {
        return (
            <Loading message="Atualizando informações da Barbearia..." />
        );
    }

    return (
        <div>
            <h2>Perfil da Barbearia</h2>
            <span>Edite as informações da barbearia</span>
            <Container className="mt-5">
                <Form onSubmit={handleSubmit}>
                    <Row className="align-items-center">
                        <Col>
                            <h3>Dados da Barbearia</h3>
                        </Col>
                        <Col className="text-end">
                            <Button 
                                variant={disabled ? 'primary' : 'secondary'} 
                                onClick={() => setDisabled(!disabled)}
                            >
                                {disabled ? 'Editar' : 'Cancelar edição'}
                            </Button>
                        </Col>
                    </Row>
                    
                    <Form.Group className="mt-3" as={Row} controlId="formNome">
                        <Form.Label column sm={2}>Nome</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Seu nome"
                                value={nome}
                                disabled={disabled}
                                onChange={(e) => setNome(e.target.value)}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="mt-2" as={Row} controlId="formNome">
                        <Form.Label column sm={2}>CNPJ</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Seu CNPJ"
                                value={cnpj}
                                disabled={disabled}
                                onChange={(e) => setCnpj(e.target.value)}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="mt-2" as={Row} controlId="formNome">
                        <Form.Label column sm={2}>Endereco</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Seu endereço"
                                value={endereco}
                                disabled={disabled}
                                onChange={(e) => setEndereco(e.target.value)}
                            />
                        </Col>
                    </Form.Group>

                    <Form.Group className="mt-2" as={Row} controlId="formNome">
                        <Form.Label column sm={2}>Telefone</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Seu telefone"
                                value={telefone}
                                disabled={disabled}
                                onChange={(e) => setTelefone(e.target.value)}
                            />
                        </Col>
                    </Form.Group>

                    <h3 className="mt-5">Informações de usuário</h3>

                    <Form.Group className="mt-3" as={Row} controlId="formEmail">
                        <Form.Label column sm={2}>Email</Form.Label>
                        <Col sm={10}>
                            <Form.Control
                                type="text"
                                placeholder="Seu email"
                                value={email}
                                disabled={disabled}
                                onChange={(e) => setEmail(e.target.value)}
                            />
                        </Col>
                    </Form.Group>

                    <Row className="align-items-center mt-5">
                        <Col>
                            <h3>Horário de Funcionamento</h3>
                        </Col>
                        <Col className="text-end">
                            <Button 
                                variant={disabledHorarioFuncionamento ? 'primary' : 'secondary'} 
                                onClick={() => setDisabledHorarioFuncionamento(!disabledHorarioFuncionamento)}
                            >
                                {disabledHorarioFuncionamento ? 'Editar' : 'Cancelar edição'}
                            </Button>
                        </Col>
                    </Row>

                    {Object.keys(DiaDaSemana).map(key => {
                        const diaEnum = DiaDaSemana[key as keyof typeof DiaDaSemana];
                        const horarioAtual = horarios.find(horario => mapDiaDaSemana(horario.diaDaSemana) === diaEnum);
                        
                        return (
                            <Col xs={12} key={diaEnum}>
                                <HorarioFuncionamentoForm 
                                    diaDaSemana={diaEnum} 
                                    horario={horarioAtual} 
                                    disabled={disabledHorarioFuncionamento} 
                                />
                            </Col>
                        )
                    })}

                    <Form.Group className="mt-3" as={Row}>
                        <Col sm={{ span: 10, offset: 2 }} className="text-end">
                            <Button variant="primary" type="submit">Salvar</Button>
                        </Col>
                    </Form.Group>
                    
                </Form>
            </Container>

            <ToastContainer position="top-end" className="p-3">
                <Toast onClose={() => setShowToastSuccess(false)} show={showToastSuccess} delay={5000} autohide bg="success">
                    <Toast.Body className="text-white"><MdCheck size={20}/> {messageToast}</Toast.Body>
                </Toast>
            </ToastContainer>

            <ToastContainer position="top-end" className="p-3">
                <Toast onClose={() => setShowToastError(false)} show={showToastError} delay={5000} autohide bg="danger">
                    <Toast.Body className="text-white"><MdError size={20}/> {messageToast}</Toast.Body>
                </Toast>
            </ToastContainer>
        </div>
    );
}