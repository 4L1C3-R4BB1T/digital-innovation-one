import styled from 'styled-components';

export const ItemContainer = styled.div `
    width: 80%;
    margin: 30px 0;
    font-family: 'Arial';

    display: flex;
    flex-direction: column;
    gap: 20px;

    div.repo {
        display: flex;
        flex-direction: row;
        align-items: center;
        justify-content: space-between;
        flex-wrap: wrap;
        
    }

    div.names {
        margin-bottom: 10px;
    }

    div.buttons {
        margin-top: -10px;
        margin-right: 20px;
    }

    h3 {    
        font-size: 32px;
        color: #fafafa;
    }

    p {
        font-size: 16px;
        color: #fafafa60;
        margin-top: 5px;
        margin-bottom: 20px;
    }

    a {
        padding: 10px;
        font-weight: bold;
        border-radius: 10px;
        text-decoration: none;
        background-color: #fafafa;
    }

    a.remover {
        color: #ff0000;
        margin-top: 20px;
        margin-left: 20px;
    }

    a:hover {
        opacity: 0.7;
    }

    a:first-child:visited {
        color: blue;
    }

    hr {
        color: #fafafa60;
    }
`