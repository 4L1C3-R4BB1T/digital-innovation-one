import styled from 'styled-components';

export const Container = styled.div `
    width: 100%;
    height: 100vh;
    background-color: lightgray;
    display: flex;
    align-items: center;
    justify-content: center;
`

export const Content = styled.div `
    width: 300px;
    border: 3px solid #000;
    background-color: #fff;
`

export const Row = styled.div `
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
`

export const Column = styled.div `
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    align-items: center;
`