import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import "./Administrador.scss";
import DrawerComponent from "../../components/Drawer/Drawer";

export default function Administrador() {
  const state = useSelector((state) => state?.user);
  const userData = useSelector((state) => state?.userDados?.role);
  const navigate = useNavigate();
  const [, setIsAdmin] = useState(true);

  useEffect(() => {
    if (state?.isLogged === false) {
      navigate("/");
    } else if (userData !== "ADMIN") {
      setIsAdmin(false);
      navigate("/");
    }
  }, [navigate, state, userData]);

  return (
    <>
      <DrawerComponent />
    </>
  );
}
