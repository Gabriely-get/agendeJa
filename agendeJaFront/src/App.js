import Header from './components/Header/Header';
import Login from './components/Login/Login';
import Cadastrar from './components/Cadastrar/Cadastrar';
import './styles/app.scss';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';


function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/cadastrar" element={<Cadastrar />} />
      </Routes>
    </Router>
  );
}

export default App;
