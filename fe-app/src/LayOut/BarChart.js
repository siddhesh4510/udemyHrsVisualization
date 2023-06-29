import { Bar } from "react-chartjs-2";
import Chart from 'chart.js/auto';
import { Paper ,Grid} from '@mui/material';
import Button from '@mui/material/Button';
import FileUploadIcon from '@mui/icons-material/FileUpload';
import DownloadIcon from '@mui/icons-material/Download';


function App(props) {
  return (
    <div className="App">
      <h3 style={{color:"#232f3e"}}>{props.userName}</h3>
      <div>
        <Bar
          data={props.graphData}
          height={400}
          options={{
            maintainAspectRatio: false,
            scales: {
            
              x: {
                stacked: true,
                ticks: {
                    maxRotation: 90, // Adjust the rotation angle as needed
                    // maxTicksLimit: 5, // Adjust the number of visible ticks as needed
      callback: function (value) {
        console.log("values",props.graphData.labels[value])
        if (props.graphData.labels[value].length > 10) { // Adjust the character limit as needed
          return props.graphData.labels[value].substr(0, 10) + "..."; // Truncate the label and add ellipsis
        }
        return props.graphData.labels[value];
      },
    
                  },
                  type:'category'
              },
              y: {
                stacked: true,
                ticks: {
                  beginAtZero: true,

                },
              },
            },
            plugins: {
              tooltip: {
                mode: "index",
                intersect: false,
            },
              legend: {
                display: true,
                position: "bottom",
              },
            },
          }}
        />
      </div>
      <div style={{position:"fixed",bottom:"0",right:"0"}}>
        <Grid container>
          <Grid item xs={6}>
        <Button variant="contained" 
        style={{backgroundColor:"#24b082",width:"200px",margin:"5px"}} 
        startIcon={<FileUploadIcon />}
        onClick={()=>{
          props.setOpenUpload(true);
          }}>
        Upload Data
      </Button>
      </Grid>
      <Grid item xs={6}>
      <Button variant="contained" 
        style={{ backgroundColor:"#24b082",width:"200px",margin:"5px"}} 
        startIcon={<DownloadIcon />}
        onClick={()=>{
          var requestOptions = {
            method: 'GET',
            redirect: 'follow'
          };
          
          fetch(`${process.env.REACT_APP_BACKEND_ENDPOINT}/fetchDynamoDBdata`, requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(result)
                // handleClose()
            })
            .catch(error => console.log('error', error));

        }}>
        Fetch Data
      </Button>
      </Grid>
      </Grid>
      </div>
    </div>
  );
}

export default App;
