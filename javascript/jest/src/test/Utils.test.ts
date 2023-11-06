import { toUpper } from "../app/Utils";
describe('Utils test suite', () => {
    it("should return upper", ()  => {
         // ACT (sut = sys under test)
        const sut = toUpper;
        const expected = 'ABC';

        // act
        const actual = toUpper('abc');

        //assert
        const result = toUpper("abc");
        expect(actual).toBe(expected);
    })
})

describe("Test toUpp with params", () => {
    beforeAll(() => {
            //before all 1 initial step
            console.log("Before All");
    }   )
    beforeEach(() => {
        //before each step
        console.log("Before Each");
}   )
    it.each([
        {input: 'abc', expected: 'ABC'},
        {input: 'My Name', expected: 'MY NAME'},
        {input: '1a', expected: '1A'}
    ])("$input toUpper should be $expected", ({input, expected}) => {  
        const actual = toUpper(input);
        expect(actual).toEqual(expected);
    })

    it.todo("todo");
    it.skip("skip",()=>{
        //skipping
    });
})