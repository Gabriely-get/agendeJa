import React from "react";
import ReactDOM from "react-dom/client";
import reportWebVitals from "./reportWebVitals";
import "./styles/index.scss";
import App from "./App";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Home from "./pages/Home/Home";
import Login from "./pages/Login/Login";
import Cadastrar from "./pages/Cadastrar/Cadastrar";
import ErrorPage from "./pages/ErrorPage/ErrorPage";
import { Provider } from "react-redux";
import store from "./redux/store";

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
