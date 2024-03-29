/** @type {import('ts-jest').JestConfigWithTsJest} 
module.exports = {
  preset: 'ts-jest',
  testEnvironment: 'node',
};
*/
import type { Config} from '@jest/types'

const config: Config.InitialOptions = {
  preset: 'ts-jest',
  testEnvironment: 'node',
  verbose: true
}
export default config;