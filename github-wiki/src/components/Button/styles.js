import styled from 'styled-components';

export const ButtonContainer = styled.button `
    width: 80%;
    height: 62px;
    margin: 20px;
    font-size: 20px;
    font-weight: bold;
    border: 1px solid #fafafa;
    border-radius: 20px;
    background-color: #fafafa;

    &:hover {
        border: 1px solid #fafafa40;
        background-color: #fafafa40;
        cursor: pointer;
    }
`