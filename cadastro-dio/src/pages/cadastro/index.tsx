import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { MdEmail, MdLock, MdPerson } from 'react-icons/md';
import { Button } from '../../components/Button';
import { Header } from '../../components/Header';
import { Input } from '../../components/Input';
import { useForm } from 'react-hook-form';
import { IFormData } from './types';

import {
    Column,
    Container,
    LoginText,
    Row,
    SubtitleSignUp,
    TextSignUp,
    Title,
    TitleSignUp,
    Wrapper,
} from './styles';

const Cadastro = () => {
    const navigate = useNavigate();

    const {
        control,
        handleSubmit,
        formState: { errors },
    } = useForm<IFormData>({
        reValidateMode: "onChange",
        mode: "onChange",
    });

    const onSubmit = () => {
        navigate("/feed");
    };

    return (
        <>
            <Header />
            <Container>
                <Column>
                    <Title>
                        A plataforma para você aprender com experts, dominar as principais tecnologias
                        e entrar mais rápido nas empresas mais desejadas.
                    </Title>
                </Column>
                <Column>
                    <Wrapper>
                        <TitleSignUp>Comece agora grátis</TitleSignUp>
                        <SubtitleSignUp>Crie sua conta e make the change._</SubtitleSignUp>
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <Input
                                placeholder="Nome completo"
                                leftIcon={<MdPerson />}
                                name="nome"
                                control={control}
                            />
                            {errors.nome && <span>Nome é obrigatório</span>}
                            <Input
                                placeholder="E-mail"
                                leftIcon={<MdEmail />}
                                name="email"
                                control={control}
                            />
                            {errors.email && <span>E-mail é obrigatório</span>}
                            <Input
                                type="password"
                                placeholder="Senha"
                                leftIcon={<MdLock />}
                                name="senha"
                                control={control}
                            />
                            {errors.senha && <span>Senha é obrigatório</span>}
                            <Button title="Criar minha conta" variant="secondary" type="submit" />
                        </form>
                        <TextSignUp>
                            Ao clicar em "criar minha conta grátis", declaro que aceito as
                            Políticas de Privacidade e os Termos de Uso da DIO.
                        </TextSignUp>
                        <Row>
                            <p>Já tenho conta.</p>
                            <Link to="/login">
                                <LoginText>Fazer login</LoginText>
                            </Link>
                        </Row>
                    </Wrapper>
                </Column>
            </Container>
        </>
    );
};

export { Cadastro };
