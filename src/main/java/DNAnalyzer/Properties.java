/*
 * Copyright © 2022 DNAnalyzer. Some rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * You are entirely responsible for the use of this application, including any and all activities that occur.
 * While DNAnalyzer strives to fix all major bugs that may be either reported by a user or discovered while debugging,
 * they will not be held liable for any loss that the user may incur as a result of using this application, under any circumstances.
 *
 * For further inquiries, please reach out to contact@dnanalyzer.live
 */
package DNAnalyzer;

import DNAnalyzer.aminoAcid.AminoAcid;
import DNAnalyzer.aminoAcid.AminoAcidFactory;

import java.util.Map;
import java.util.Arrays;
import java.util.List;
import DNAnalyzer.DNAAnalysis.BasePairIndex;
import static DNAnalyzer.DNAAnalysis.countBasePairs;

/**
 * Prints the list of proteins and their respective properties found in the DNA.
 *
 * @author Piyush Acharya (@Verisimilitude11)
 * @version 1.2.1
 */
public class Properties {

    /**
     * Prints the list of proteins found in the DNA.
     *
     * @param proteinList The list of proteins to be printed
     * @param aminoAcid   The amino acid to be searched for
     *                    {@code @category} Output
     */
    public static void printProteinList(final List<String> proteinList, final String aminoAcid) {

        // Changes the 1 letter or 3 letter abbreviation of the amino acids into the
        // full name
        final AminoAcid acid = AminoAcidFactory.getAminoAcid(aminoAcid);

        System.out.println("Proteins coded for " + acid.getFullName() + ": ");
        System.out.println("----------------------------------------------------");
        short count = 1;
        for (final String gene : proteinList) {
            System.out.println(count + ". " + gene);
            count++;
        }
    }

    /**
     * Gets the GC content of a gene.
     *
     * @param dna The DNA sequence to be analyzed
     * @return The GC content of the DNA sequence
     *         {@code @category} Properties
     * @see "https://www.sciencedirect.com/topics/biochemistry-genetics-and-molecular-biology/gc-content"
     */
    public static float getGCContent(String dna) {
        dna = dna.toLowerCase();
        float gcLen = 0;
        // increment gcLen for each 'g' or 'c' encountered in a dna
        for (final char letter : dna.toCharArray()) {
            if ((letter == 'c') || (letter == 'g')) {
                gcLen++;
            }
        }
        return (gcLen / dna.length());
    }

    /**
     * Prints the nucleotide count of the DNA sequence.
     *
     * @param dna The DNA sequence that was analyzed
     *            {@code @category} Output
     */
    public static void printNucleotideCount(final String dna) {
        System.out.println("Nucleotide count:");
        long[] counts = countBasePairs(dna);
        System.out.println("A" + ": " + counts[BasePairIndex.ADENINE] +
                " (" + (float) counts[BasePairIndex.ADENINE] / dna.length() * 100 + "%)");
        System.out.println("T" + ": " + counts[BasePairIndex.THYMINE] +
                " (" + (float) counts[BasePairIndex.THYMINE] / dna.length() * 100 + "%)");
        System.out.println("G" + ": " + counts[BasePairIndex.GUANINE] +
                " (" + (float) counts[BasePairIndex.GUANINE] / dna.length() * 100 + "%)");
        System.out.println("C" + ": " + counts[BasePairIndex.CYTOSINE] +
                " (" + (float) counts[BasePairIndex.CYTOSINE] / dna.length() * 100 + "%)");
    }

    /**
     * Checks if the DNA sequence is random or not.
     *
     * @param dna The DNA sequence that was analyzed
     * @return Whether the DNA sequence is random or not
     *         {@code @category} Properties
     */
    public static boolean isRandomDNA(final String dna) {
        long[] nucleotideCount = countBasePairs(dna);

        // This sorts the array to get min and max value
        Arrays.sort(nucleotideCount);

        // Only calculate 2 Percentages, as only the highest difference (max - min) is
        // relevant
        final int maxPercent = nucleotidePercentage(nucleotideCount[3], dna);
        final int minPercent = nucleotidePercentage(nucleotideCount[0], dna);
        // If the percentage of each nucleotide is between 2% of one another, then it is
        // random
        return (maxPercent - minPercent) <= 2;
    }

    /**
     * Calculates the percentage of given amount of nucleotide in the dna sequence/
     *
     * @param nucleotideCount Number of nucleotides (int)
     * @param dna             DNA sequence
     * @return the percentage of nucleotides in that DNA sequence
     */

    private static int nucleotidePercentage(final long nucleotideCount, final String dna) {
        return (int) (((float) nucleotideCount) / dna.length() * 100);
    }
}
