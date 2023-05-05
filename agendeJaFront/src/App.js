import Header from "./components/Header/Header";
import "./styles/app.scss";
import { Outlet } from "react-router-dom";
import { ChakraProvider } from "@chakra-ui/react";
import Footer from "./components/Footer/Footer";

function App() {
  return (
    <ChakraProvider>
      <div className="App">
        <Header />
        <div className="content">
          <Outlet />
        </div>
        <Footer />
      </div>
    </ChakraProvider>
  );
}

export default App;
