import React from "react";
import ReactDOM from "react-dom/client";
import reportWebVitals from "./reportWebVitals";
import "./styles/index.scss";
import App from "./App";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import Cadastrar from "./pages/Cadastrar/Cadastrar";
import CadastrarEmpresa from "./pages/CadastrarEmpresa/CadastrarEmpresa";
import ErrorPage from "./pages/ErrorPage/ErrorPage";
import { Provider } from "react-redux";
import store from "./redux/store";
import MinhaConta from "./pages/MinhaConta/MinhaConta";
import Administrador from "./pages/Administrador/Administrador";
import RegistroCategoriaEmpresa from "./pages/RegistroCategoriaEmpresa/RegistroCategoriaEmpresa";
import ADMSearchUser from "./pages/Administrador/User/ADMSearchUser";
import ADMCreateCategory from "./pages/Administrador/Category/Create/ADMCreateCategory";
import ADMCreateSubCategory from "./pages/Administrador/SubCategory/Create/ADMCreateSubCategory";
import ADMUpdateCategory from "./pages/Administrador/Category/Update/ADMUpdateCategory";
import ADMUpdateSubCategory from "./pages/Administrador/SubCategory/Update/ADMUpdateSubCategory";
import ADMCreateJob from "./pages/Administrador/Job/Create/ADMCreateJob";
import ADMUpdateJob from "./pages/Administrador/Job/Update/ADMUpdateJob";
import CreateFilial from "./pages/Empresa/CreateFilial/CreateFilial";
import ServicosPortifolio from "./pages/Empresa/ServicosPortifolio/ServicosPortifolio";
import CreatePortifolio from "./pages/Empresa/CreatePortifolio/CreatePortifolio";
import ServicosFilial from "./pages/Empresa/ServicosFilial/ServicosFilial";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <Home />,
      },
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/cadastrar",
        element: <Cadastrar />,
      },
      {
        path: "/cadastro-empresa",
        element: <CadastrarEmpresa />,
      },
      {
        path: "/cadastro-empresa/seu-negocio",
        element: <RegistroCategoriaEmpresa />,
      },
      {
        path: "/minhaconta",
        element: <MinhaConta />,
      },
      {
        path: "/administrador",
        element: <Administrador />,
      },
      {
        path: "/administrador/visualizar_todos_usuarios",
        element: <ADMSearchUser />,
      },
      {
        path: "/administrador/criar_categoria",
        element: <ADMCreateCategory />,
      },
      {
        path: "/administrador/editar_categoria",
        element: <ADMUpdateCategory />,
      },
      {
        path: "/administrador/criar_subcategoria",
        element: <ADMCreateSubCategory />,
      },
      {
        path: "/administrador/editar_subcategoria",
        element: <ADMUpdateSubCategory />,
      },
      {
        path: "/administrador/criar_job",
        element: <ADMCreateJob />,
      },
      {
        path: "/administrador/editar_job",
        element: <ADMUpdateJob />,
      },
      {
        path: "/empresa/criar_filial",
        element: <CreateFilial />,
      },
      {
        path: "/empresa/servicos_filial/:id",
        element: <ServicosFilial />,
      },
      {
        path: "/empresa/criar_portifolio",
        element: <CreatePortifolio />,
      },
      {
        path: "/empresa/servicos_portifolio/:id",
        element: <ServicosPortifolio />,
      },
    ],
  },
]);

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <React.StrictMode>
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
