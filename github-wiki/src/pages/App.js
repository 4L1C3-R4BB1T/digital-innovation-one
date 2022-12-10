import { useState } from 'react';
import gitLogo from '../assets/github.png';
import Button from '../components/Button';
import Input from '../components/Input';
import ItemRepo from '../components/ItemRepo';
import { api } from '../services/api';
import { Container } from './styles';

function App() {
    const [repos, setRepos] = useState([]);
    const [currentRepo, setCurrentRepo] = useState('');

    const handleSearchRepo = async () => {
        const {data} = await api.get(`repos/${currentRepo}`).catch(() => {
                alert('Repositório não encontrado!');
                return;
            }
        );

        if (data.id) {
            const isExist = repos.find(repo => repo.id === data.id);

            if (!isExist) {
                setRepos(prev => [...prev, data]);
                setCurrentRepo('');
                return;
            }
        }
    }

    const handleRemoveRepo = (id) => {
        setRepos(repos.filter(repo => id !== repo.id));
    }

    return (
        <Container>
            <img src={gitLogo} width={72} height={72} alt="GitHub Logo" />
            <Input value={currentRepo} onChange={(e) => setCurrentRepo(e.target.value)} />
            <Button onClick={handleSearchRepo} />
            {repos.map(repo => <ItemRepo repo={repo} handleRemoveRepo={handleRemoveRepo} />)}
        </Container>
    );
}

export default App;