package practice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import model.Candidate;
import model.Cat;
import model.Person;

public class StreamPractice {
    public int findMinEvenNumber(List<String> numbers) {
        return numbers.stream()
                .flatMap(num -> Stream.of(num.split(",")))
                .mapToInt(Integer::parseInt)
                .filter(digit -> digit % 2 == 0)
                .min()
                .orElseThrow(() ->
                        new RuntimeException("Can't get min value from list:" + numbers));
    }

    public Double getOddNumsAverage(List<Integer> numbers) {
        return IntStream.range(0, numbers.size())
                .map(i -> i % 2 == 1 ? numbers.get(i) - 1 : numbers.get(i))
                .filter(num -> num % 2 == 1)
                .average()
                .orElseThrow(NoSuchElementException::new);
    }

    public List<Person> selectMenByAge(List<Person> peopleList, int fromAge, int toAge) {
        Predicate<Person> personPredicate = new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getSex().equals(Person.Sex.MAN)
                        && person.getAge() >= fromAge
                        && person.getAge() <= toAge;
            }
        };
        return peopleList.stream()
                .filter(personPredicate)
                .collect(Collectors.toList());
    }

    public List<Person> getWorkablePeople(int fromAge, int femaleToAge,
                                          int maleToAge, List<Person> peopleList) {
        Predicate<Person> personPredicate = new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                if (person.getSex().equals(Person.Sex.MAN)) {
                    return person.getAge() >= fromAge && person.getAge() <= maleToAge;
                }
                return person.getAge() >= fromAge && person.getAge() <= femaleToAge;
            }
        };
        return peopleList.stream()
                .filter(personPredicate)
                .collect(Collectors.toList());
    }

    public List<String> getCatsNames(List<Person> peopleList, int femaleAge) {
        return peopleList.stream()
                .filter(person -> person.getSex().equals(Person.Sex.WOMAN)
                        && person.getAge() >= femaleAge)
                .flatMap(person -> person.getCats().stream().map(Cat::getName))
                .collect(Collectors.toList());
    }

    public List<String> validateCandidates(List<Candidate> candidates) {
        CandidateValidator candidateValidator = new CandidateValidator();
        return candidates.stream()
                .filter(candidateValidator)
                .map(Candidate::getName)
                .sorted()
                .collect(Collectors.toList());
    }
}
