import styled from 'styled-components';

export const ButtonContainer = styled.button`
    width: 100%;
    height: 42px;
    background-color: #81259d;
    color: #fff;
    border: 1px solid #81259d;
    border-radius: 21px;
    font-size: 14px;

    &:hover {
        opacity: 0.6;
        cursor: pointer;
    }

    &:disabled {
        color: #666666;
        border: 1px solid #999999;
        background-color: #cccccc;
        cursor: not-allowed;
    }
`
