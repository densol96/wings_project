import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import axios from 'axios';


export default function SingleNews() {
    const {id} = useParams();
    const [news, setNews] = useState(null);
    const [error, setError] = useState(null);

    const getData = async (e) => {
        useEffect(() => {
          axios
            .get(`http://localhost:8080/api/news/show/${id}`)
            .then((res) => {
              setNews(res.data.result);
            })
            .catch((error) => {
              setError(error);
            });
        }, []);
      };

      getData()

  return (
     <>
      {news &&  (
        <>
      <h2 className='text-2xl font-bold text-center'>{news.nosaukums}</h2> 
      <h2 className='text-2xl font-bold text-center'>{news.sakumsDatums}</h2> 
      <h2 className='text-2xl font-bold text-center'>{news.beiguDatums}</h2> 
      <h2 className='text-2xl font-bold text-center'>{news.vieta}</h2> 
      <h2 className='text-2xl font-bold text-center'>{news.apraksts}</h2> 
        </>
      )}
    </>
  )
}
