import http from "k6/http";
import { check } from 'k6';


export const options = {
  scenarios: {
    constant_request_rate: {
      executor: 'constant-arrival-rate',
      rate: 10,
      timeUnit: '1s', // 10 iterations per second, i.e. 10 RPS
      duration: '1h',
      preAllocatedVUs: 5, // how large the initial pool of VUs would be
      maxVUs: 10, // if the preAllocatedVUs are not enough, we can initialize more
    },
  },
};

export default function () {
  http.get("http://localhost:8080/fake-data/read-traffic/");
  http.get("http://localhost:8080/fake-data/write-traffic");
  const responses = http.batch([
    ['GET', 'http://localhost:8080/fake-data/read-traffic/'],
    ['GET', 'http://localhost:8080/fake-data/write-traffic/']
  ]);

  check(responses[0], {'main page status was 200': (res) => res.status === 200,});
  check(responses[1], {'main page status was 200': (res) => res.status === 200,});
}
