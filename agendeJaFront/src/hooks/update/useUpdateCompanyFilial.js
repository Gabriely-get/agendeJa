import axios from "axios";

export default function useUpdateCompanyFilial() {
  const apiUrl = process.env.REACT_APP_API_AGENDEJA_AWS;
  const updateCompanyFilial = async (
    id,
    fantasyName,
    isCep,
    isLogradouro,
    isComplemento,
    isBairro,
    isCidade,
    isEstado,
    isNumero,
    userId
  ) => {
    let url = `${apiUrl}:5000/agenda/company/${id}`;

    try {
      const response = await axios.put(url, {
        fantasyName: fantasyName,
        userId: userId,
        address: {
          cep: isCep,
          logradouro: isLogradouro,
          complemento: isComplemento,
          bairro: isBairro,
          localidade: isCidade,
          uf: isEstado,
          numero: isNumero,
        },
      });

      return response.data;
    } catch (error) {
      throw error;
    }
  };

  return {
    updateCompanyFilial,
  };
}
