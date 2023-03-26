import Header from './components/Header/Header';
import './styles/app.scss';
import { Outlet } from 'react-router-dom';


function App() {
  return (
    <div className='App'>
      <Header/>
      <div className='content'>
      <Outlet />
      </div>
    </div>
  );
}

export default App;
