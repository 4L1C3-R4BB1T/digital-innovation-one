import styled from 'styled-components';

export const InputContainer = styled.div`
    width: 100%;
    height: 42px;
    color: #fff;
    border: 1px solid #81259d;
    border-radius: 21px;
    overflow: hidden;
    
    & input {
        width: 100%;
        height: 42px;
        border-radius: 21px;
        border: 0;
        background-color: transparent;
        outline: none;
        padding: 0 20px;
        font-size: 14px;
    }
`

export const ErrorMessage = styled.p`
    color: red;
    font-size: 14px;
    margin-top: 8px;
    margin-left: 10px;
`
