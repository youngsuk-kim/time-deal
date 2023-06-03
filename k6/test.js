import http from 'k6/http';
import { URL } from 'https://jslib.k6.io/url/1.0.0/index.js';
import { check } from 'k6';
import { randomIntBetween } from "https://jslib.k6.io/k6-utils/1.1.0/index.js";

export let options = {
  vus: 2000,
  duration: '10s',
};

export default function () {

  const url = new URL('http://localhost:8080/users/buy/1');

  url.searchParams.append('quantity', 1);
  url.searchParams.append('userId', 1);

  const res = http.post(url.toString());

  check(res, {
    'is status 200': (r) => r.status === 200,
  });
}