import {BrowserRouter as Router, Route} from 'react-router-dom'
import Home from './routes/Home'
import Room from './routes/Room';

const App = () => {
  return (
    <div className="App">
      <Router>
        <Route path='/' exact render={Home} />
        <Route path='/:id' render={(props) => <Room {...props} />} />
      </Router>
    </div>
  );
}

export default App;
